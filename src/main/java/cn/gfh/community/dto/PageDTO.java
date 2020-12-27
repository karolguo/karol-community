package cn.gfh.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-24 9:38
 * 分页dto
 */
@Data
public class PageDTO {
    private List<QuestionDTO> question;
    private Boolean showPrevious;//判断是否有上一页
    private Boolean showNext;//判断是否有下一页
    private Boolean showFirstPage;//判断是否有第一页
    private Boolean showEndPage;//判断是否显示尾页
    private Integer totalPage ;
    private Integer page;//当前页

    private List<Integer> pages = new ArrayList<>();//显示页数

    public void setPageDTO(Integer totalCount, Integer page, Integer size) {


        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //判断页数越界
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        this.page = page;
        //
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0,page - i);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
