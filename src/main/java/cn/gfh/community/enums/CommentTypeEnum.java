package cn.gfh.community.enums;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-11-04 17:03
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    /**
     * @return boolean
     * @description:
     * @author:Karol Guo
     * @date:2019/12/1
     * @param: type
     */
    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }

        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
