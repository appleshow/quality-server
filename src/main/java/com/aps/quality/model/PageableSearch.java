package com.aps.quality.model;

import com.aps.quality.util.Const;
import com.aps.quality.util.DataUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.data.domain.*;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class PageableSearch implements Serializable {
    @ApiModelProperty(name = "page", required = false, example = "0", notes = "当前页码，从0开始；默认0")
    private Integer page;
    @ApiModelProperty(name = "perPage", required = false, example = "10", notes = "每页记录数；默认10")
    private Integer perPage;
    @ApiModelProperty(name = "sortField", required = false, example = "createTime", notes = "排序列名")
    private String sortField;
    @ApiModelProperty(name = "sortAsc", required = false, example = "true", notes = "是否降序，false则为升序")
    private boolean sortAsc;
    @ApiModelProperty(name = "pageStart", required = false, example = "0", notes = "起始页，默认0")
    private Integer pageStart;

    public Pageable getDefaultPageable() {
        final List<Sort.Order> orders = new ArrayList<>();

        if (!StringUtils.hasLength(getSortField())) {
            orders.add(new Sort.Order(Sort.Direction.DESC, Const.CREATE_TIME));
        } else {
            orders.add(new Sort.Order(isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, getSortField()));
        }

        return PageRequest.of(getRealPage(),
                getPerPage() == null ? Const.DEFAULT_NUMBER_PER_PAGE : getPerPage(),
                Sort.by(orders));
    }

    public Pageable getDefaultPageable(String defaultOrderBy) {
        final List<Sort.Order> orders = new ArrayList<>();

        if (!StringUtils.hasLength(getSortField())) {
            orders.add(new Sort.Order(Sort.Direction.DESC, defaultOrderBy));
        } else {
            orders.add(new Sort.Order(isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, getSortField()));
        }

        return PageRequest.of(getRealPage(),
                getPerPage() == null ? Const.DEFAULT_NUMBER_PER_PAGE : getPerPage(),
                Sort.by(orders));
    }

    public Pageable getDefaultPageable(String... defaultOrderBy) {
        final List<Sort.Order> orders = new ArrayList<>();

        if (!StringUtils.hasLength(getSortField())) {
            Arrays.stream(defaultOrderBy).forEach(s -> orders.add(new Sort.Order(Sort.Direction.DESC, s)));
        } else {
            orders.add(new Sort.Order(isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, getSortField()));
        }

        return PageRequest.of(getRealPage(),
                getPerPage() == null ? Const.DEFAULT_NUMBER_PER_PAGE : getPerPage(),
                Sort.by(orders));
    }

    public Pageable getDefaultPageable(Sort.Order... defaultOrder) {
        final List<Sort.Order> orders = new ArrayList<>();

        if (!StringUtils.hasLength(getSortField())) {
            Arrays.stream(defaultOrder).forEach(o -> orders.add(o));
        } else {
            orders.add(new Sort.Order(isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, getSortField()));
        }

        return PageRequest.of(getRealPage(),
                getPerPage() == null ? Const.DEFAULT_NUMBER_PER_PAGE : getPerPage(),
                Sort.by(orders));
    }

    public Sort getDefaultSort(Sort.Order... defaultOrder) {
        final List<Sort.Order> orders = new ArrayList<>();

        if (!StringUtils.hasLength(getSortField())) {
            Arrays.stream(defaultOrder).forEach(o -> orders.add(o));
        } else {
            orders.add(new Sort.Order(isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, getSortField()));
        }

        return Sort.by(orders);
    }

    public <S, D> Page<D> exchange(ConfigurableMapper mapper, Page<S> s, Pageable pageable, Class d) {
        final List<S> source = Optional.ofNullable(s).map(Page::getContent).orElse(new ArrayList<>());

        return new PageImpl<>(mapper.mapAsList(source, d), pageable, s.getTotalElements());
    }

    private Integer getRealPage() {
        final int pageStartReal = DataUtil.nvl(this.pageStart, 0);
        final int pageReal = DataUtil.nvl(this.page, Const.DEFAULT_PAGE_INDEX);

        return pageReal - pageStartReal < 0 ? 0 : pageReal - pageStartReal;
    }
}