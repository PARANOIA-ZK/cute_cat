package com.paranoia.mongo.common;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Pager
 * @Description: 列表分页DTO
 * @author:Norcy
 */
@Data
public class Pager<T> implements Serializable {

    private static final long serialVersionUID = -7846618338657036050L;

    //默认的每页记录数
    public static final int DEFAULT_PAGE_SIZE = 10;

    // 每页的记录数
    private int pageSize = DEFAULT_PAGE_SIZE;

    // 当前页
    private int pageNo = 1;

    // 总行数
    private int records;

    // 总页数
    private int total;

    // 每页的记录
    private List<T> resultList;

    public Pager(){
    }
    public Pager(int pageNo, int pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }


    public Pageable getPageable() {
        Pageable pageable = new PageRequest(this.pageNo - 1, this.pageSize);
        return pageable;
    }

}
