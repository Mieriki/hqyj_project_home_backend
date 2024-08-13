package com.mugen.inventory.utils.constant;

/**
 * Http 响应码常量类
 */
public interface HttpMessageConstant {
    //请求的初始部分已被接受，客户端应继续请求。
    int CONTINUE = 100;
    String CONTINUE_MESSAGE = "请继续发送请求";

    //服务器正在根据客户端的请求切换协议。
    int SWITCHING_PROTOCOLS = 101;
    String SWITCHING_PROTOCOLS_MESSAGE = "正在切换协议";

    // 请求已成功处理
    int OK = 200;
    String OK_MESSAGE = "请求成功";

    // 请求已经被实现，并且有一个新的资源已经依据请求的需要而创建
    int CREATED = 201;
    String CREATED_MESSAGE = "资源创建成功";

    //服务器已经接收到请求，但尚未进行处理。
    int ACCEPTED = 202;
    String ACCEPTED_MESSAGE = "请求已被接受";

    // 服务器成功处理了请求，但未返回任何内容。
    int NO_CONTENT = 204;
    String NO_CONTENT_MESSAGE = "请求处理成功";

    // 请求的网页已永久移动到新位置
    int MOVED_PERMANENTLY = 301;
    String MOVED_PERMANENTLY_MESSAGE = "网页已永久移动到新位置";

    // 请求的网页已临时移动到新位置
    int FOUND = 302;
    String FOUND_MESSAGE = "网页临时移动到新位置";

    // 服务器判断资源未被修改时，告诉客户端可以使用缓存的版本。
    int NOT_MODIFIED = 304;
    String NOT_MODIFIED_MESSAGE = "请使用本地缓存的资源";

    // 服务器无法理解客户端的请求，语法格式有误
    int BAD_REQUEST = 400;
    String BAD_REQUEST_MESSAGE = "请求无效";

    // 请求要求身份验证
    int UNAUTHORIZED = 401;
    String UNAUTHORIZED_MESSAGE = "身份验证失败";

    // 服务器拒绝执行请求
    int FORBIDDEN = 403;
    String FORBIDDEN_MESSAGE = "拒绝访问";

    // 服务器无法找到请求的资源
    int NOT_FOUND = 404;
    String NOT_FOUND_MESSAGE = "服务器迷路了";

    // 客户端使用了不被允许的请求方法类型
    int METHOD_NOT_ALLOWED = 405;
    String METHOD_NOT_ALLOWED_MESSAGE = "不被允许的请求方法";

    //服务器无法根据客户端请求的内容特性完成请求。
    int NOT_ACCEPTABLE = 406;
    String NOT_ACCEPTABLE_MESSAGE = "无法处理请求的内容";

    // 请求与服务器当前状态冲突
    int CONFLICT = 409;
    String CONFLICT_MESSAGE = "请求冲突";

    //之前存在的资源现在已经永久不可用，并且服务器不会再有这个资源的备份。具体情况包括：
    int GONE = 410;
    String GONE_MESSAGE = "请求的资源已不存在";

    // 服务器不支持客户端请求中所使用的媒体类型
    int UNSUPPORTED_MEDIA_TYPE = 415;
    String UNSUPPORTED_MEDIA_TYPE_MESSAGE = "不支持的媒体类型";

    //服务器理解了客户端发送的请求，但是请求中包含的内容有误或无法处理。
    int UNPROCESSABLE_ENTITY = 422;
    String UNPROCESSABLE_ENTITY_MESSAGE = "请求内容有误";

    // 客户端发送的请求过多，服务器无法处理
    int TOO_MANY_REQUESTS = 429;
    String TOO_MANY_REQUESTS_MESSAGE = "请求频繁，请稍候再试";

    // 服务器内部错误，无法完成请求
    int INTERNAL_SERVER_ERROR = 500;
    String INTERNAL_SERVER_ERROR_MESSAGE = "服务器发生错误，请联系管理员";

    // 作为网关或代理服务器尝试执行请求时，从上游服务器接收到无效的响应
    int BAD_GATEWAY = 502;
    String BAD_GATEWAY_MESSAGE = "服务器内部错误，请联系管理员";

    // 服务器暂时过载或维护，无法处理请求
    int SERVICE_UNAVAILABLE = 503;
    String SERVICE_UNAVAILABLE_MESSAGE = "服务器暂时无法响应，请稍后再试";
}
