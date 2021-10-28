package com.dragonappear.inha.api.repository.item;


import com.dragonappear.inha.api.repository.item.dto.NotebookDto;
import com.dragonappear.inha.api.repository.item.dto.QNotebookDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.dragonappear.inha.domain.item.QItem.*;
import static com.dragonappear.inha.domain.item.QNotebook.*;


@RequiredArgsConstructor
@Repository
public class NotebookQueryRepository {

    private final JPAQueryFactory queryFactory;

    public NotebookDto findById(Long itemId) {
        NotebookDto dto = queryFactory.select(
                        new QNotebookDto(item.id
                                , item.manufacturer.manufacturerName.stringValue()
                                , item.itemName
                                , item.modelNumber
                                , item.releaseDay
                                , item.releasePrice.amount
                                , item.color
                                , item.likeCount
                                , item.latestPrice.amount
                                , notebook.inch
                                , notebook.cpu
                                , notebook.core
                                , notebook.os
                                , notebook.memory
                                , notebook.storage
                                , notebook.gpu
                                , notebook.weight)
                )
                .from(item)
                .join(notebook).on(notebook.id.eq(item.id))
                .where(item.id.eq(itemId))
                .fetchOne();
        if (dto == null) {
            throw new IllegalArgumentException("존재하지 않는 아이템입니다.");
        }
        return dto;
    }
}
