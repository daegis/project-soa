package cn.aegisa.project.exception;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/13 11:27
 */
public class DuplicatedIdNumberException extends Exception {
    public DuplicatedIdNumberException() {
    }

    public DuplicatedIdNumberException(String message) {
        super(message);
    }
}
