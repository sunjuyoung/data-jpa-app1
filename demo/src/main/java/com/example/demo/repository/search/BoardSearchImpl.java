package com.example.demo.repository.search;

import com.example.demo.entity.Board;
import com.example.demo.entity.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.example.demo.entity.QBoard.*;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);
    }


    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        JPQLQuery<Board> query = from(board);
        if(types != null && types.length>0 && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t": booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c": booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w" : booleanBuilder.or(board.writer.contains(keyword));
                    break;

                }
            }
            query.where(booleanBuilder);
        }
        query.where(board.id.gt(0L));
        this.getQuerydsl().applyPagination(pageable,query);
        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list,pageable,count);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        JPQLQuery<Board> query = from(board);
        query.where(board.title.contains("1"));

        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> fetch = query.fetch();
        long count = query.fetchCount();
        return null;
    }
}
