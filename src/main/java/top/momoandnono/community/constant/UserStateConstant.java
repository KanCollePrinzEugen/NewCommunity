package top.momoandnono.community.constant;

/**
 * 用户状态常量
 * @author l
 */
public class UserStateConstant {

    /**
     * 正常
     */
    public static final Integer NORMAL = 1;

    /**
     * 禁用
     */
    public static final Integer DISABLED = 0;

    /**
     * 删除
     */
    public static final Integer DELETE = -1;

    /**
     * 判断为正常
     * @param state 用户状态
     * @return boolean
     */
    public static boolean toNormal(Integer state) {
        return state.equals(NORMAL);
    }
}
