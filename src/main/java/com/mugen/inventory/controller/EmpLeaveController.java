package com.mugen.inventory.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugen.inventory.annotation.LoggerPermission;
import com.mugen.inventory.entity.model.vo.request.EmpLeaveStartVo;
import com.mugen.inventory.entity.model.vo.response.LeaveVo;
import com.mugen.inventory.service.AdminService;
import com.mugen.inventory.utils.JwtUtils;
import com.mugen.inventory.utils.RestBean;
import com.mugen.inventory.utils.constant.ParameterConstant;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Log4j2
@RestController
@RequestMapping("/leaves")
public class EmpLeaveController {
    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private JwtUtils utils;

    @Resource
    private AdminService adminService;


    @PostMapping("")
    public <T> RestBean<Void> createLeave() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("process/qj.bpmn20.xml")
                .name("员工请假流程")
                .deploy();
        return RestBean.success("创建请假流程成功");
    }

    @LoggerPermission(operation = "审批请假流程")
    @PostMapping("/next")
    public <T> RestBean<String> nextLeave(@RequestBody LeaveVo vo) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("outmark", "通过");
        taskService.complete(vo.getTaskId(), variables);
        if (vo.getName().equals("人事")) {
            stringRedisTemplate.delete(vo.getId());
            stringRedisTemplate.delete(ParameterConstant.LEAVE_ADMIN_FLOW_ID + vo.getUserId());
        }
        return RestBean.success(vo.getName(), "审批完成");
    }

    @GetMapping("/list")
    public <T> RestBean<List<LeaveVo>> listLeave(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt == null) {
            throw new RuntimeException("请先进行登录!");
        }
        List<String> roleNameList = adminService.queryRoleNamebYAdminId(utils.getId(jwt));
        log.info("roleNameList: {}", roleNameList);
        String roleName = "";
        List<Task> taskList = new ArrayList<>();
        if (roleNameList.contains("project") || roleNameList.contains("admin")) {
            roleName = "项目经理";
            taskList.addAll(taskService.createTaskQuery().
                    processDefinitionKey("empqingjia").
                    taskAssignee(roleName).list());
        }
        if (roleNameList.contains("personnel") || roleNameList.contains("admin")) {
            roleName = "人事经理";
            taskList.addAll(taskService.createTaskQuery().
                    processDefinitionKey("empqingjia").
                    taskAssignee(roleName).list());
        }
        return RestBean.success(taskList.stream()
                .map(task -> {
                    Map variables;
                    try {
                        variables = removeBackslashes(stringRedisTemplate.opsForValue().get(task.getProcessInstanceId()));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return LeaveVo.builder()
                            .id(task.getProcessInstanceId())
                            .taskId(task.getId())
                            .name(task.getName())
                            .username(variables.get("name").toString())
                            .reason(variables.get("reason").toString())
                            .day(variables.get("day").toString())
                            .userId((Integer) variables.get("id"))
                            .build();
                })
                .toList());
    }



    @GetMapping("/check")
    public <T> RestBean<String> checkLeave(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt == null) {
            throw new RuntimeException("请先进行登录!");
        }
        if (!Boolean.TRUE.equals(stringRedisTemplate.hasKey(ParameterConstant.LEAVE_ADMIN_FLOW_ID + utils.getId(jwt))))
            return RestBean.success(null, "未创建请假流程");
        return RestBean.success(stringRedisTemplate.opsForValue().get(ParameterConstant.LEAVE_ADMIN_FLOW_ID + utils.getId(jwt)), "请假流程ID");
    }

    /**
     * 开启请假流程
     * @param vo 请假信息
     * @return RestBean
     * @param <T> T
     */
    @LoggerPermission(operation = "开启员工请假流程")
    @PostMapping("/flow")
    public <T> RestBean<Map<String,Object>> startFlow(HttpServletRequest request, @RequestBody EmpLeaveStartVo vo){
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt == null) {
            throw new RuntimeException("请先进行登录!");
        }
        vo.setId(utils.getId(jwt));
        //启动流程实列
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("yuangong",vo.getName());
        objectMap.put("day",vo.getDay());
        objectMap.put("reason",vo.getReason());
        //启动流程
        ProcessInstance holidayRequest =
                runtimeService.startProcessInstanceByKey("empqingjia", objectMap);
        log.info("流程定义ID:{}", holidayRequest.getProcessDefinitionId());
        log.info("流程部署ID:{}", holidayRequest.getDeploymentId());
        log.info("流程实例ID:{}", holidayRequest.getId());
        log.info("流程活动ID:{}", holidayRequest.getActivityId());
        //自己提交完成之后就通过
        Task task = taskService.createTaskQuery().
                processInstanceId(holidayRequest.getId()).singleResult();
        //任务完成
        taskService.complete(task.getId());
//        return R.ok().message("开启审批流程").data("id",holidayRequest.getId()).
//                data("definitionId",holidayRequest.getProcessDefinitionId());
        Map<String,Object> map = new HashMap<>();
        map.put("id",holidayRequest.getId());
        map.put("definitionId",holidayRequest.getProcessDefinitionId());
        map.put("taskId", task.getId());
        stringRedisTemplate.opsForValue().set(ParameterConstant.LEAVE_ADMIN_FLOW_ID + utils.getId(jwt), holidayRequest.getId(), 7, TimeUnit.DAYS);
//        stringRedisTemplate.opsForValue().set(ParameterConstant.LEAVE_ADMIN_TASK_ID + utils.getId(jwt), task.getId(), 7, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(holidayRequest.getId(), vo.asJsonString(), 7, TimeUnit.DAYS);
        return RestBean.success(map);
    }

    //查看请假的进程
    @GetMapping("/get/{processId}")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, @PathVariable String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processId).singleResult();
        log.info("result of pi: {}", pi);

        // 流程走完的不显示图
        if (pi == null) {
            return;
        }

        Task task = taskService.createTaskQuery()
                .processInstanceId(pi.getId()).singleResult();
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();

        httpServletResponse.setContentType("image/png");  // 设置响应内容类型为图片格式

        try (InputStream in = diagramGenerator.generateDiagram(
                bpmnModel,
                "png",
                activityIds,
                flows,
                engconf.getActivityFontName(),
                engconf.getLabelFontName(),
                engconf.getAnnotationFontName(),
                engconf.getClassLoader(),
                1.0,
                false
        )) {
            byte[] buf = new byte[1024];
            int length;
            try (OutputStream out = httpServletResponse.getOutputStream()) {
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
            }
        }
    }

    private Map removeBackslashes(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, Map.class);
    }
}
