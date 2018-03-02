package cn.aegisa.project.common;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/7 15:35
 */
@Setter
@Getter
public class MessageResponse implements Serializable {
    private boolean success;
    private String message;

    private MessageResponse(boolean success) {
        this.success = success;
    }

    private MessageResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public static String success() {
        return JSON.toJSONString(new MessageResponse(true));
    }

    public static String success(String message) {
        return JSON.toJSONString(new MessageResponse(true, message));
    }

    public static String fail(String message) {
        return JSON.toJSONString(new MessageResponse(false, message));
    }
}
