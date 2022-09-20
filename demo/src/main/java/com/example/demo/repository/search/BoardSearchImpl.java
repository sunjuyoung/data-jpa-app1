package com.example.demo.repository.search;

import com.example.demo.dto.BoardListReplyCountDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.QBoard;
import com.example.demo.entity.QReply;
import com.example.demo.entity.Reply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.example.demo.entity.QBoard.*;
import static com.example.demo.entity.QReply.*;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<BoardListReplyCountDto> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        //
        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));
        query.groupBy(board);


        //검색조건
        if((types != null && types.length >0) && keyword != null){
            BooleanBuilder builder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t": builder.or(board.title.containsIgnoreCase(keyword));
                    break;
                    case "c" : builder.or(board.content.containsIgnoreCase(keyword));
                    break;
                    case "w" : builder.or(board.username.containsIgnoreCase(keyword));
                    break;
                }
            }
            query.where(builder);
        }
        query.where(board.id.gt(0L));

        //Projections.bean() 이용해서 dto로 처리
        JPQLQuery<BoardListReplyCountDto> dtoJPQLQuery =
                query.select(Projections.bean(BoardListReplyCountDto.class,
                        board.id,
                        board.title,
                        board.username,
                        board.createdDate,
                        reply.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable,dtoJPQLQuery);
        List<BoardListReplyCountDto> dtoList = dtoJPQLQuery.fetch();
        long count = dtoJPQLQuery.fetchCount();


        return new PageImpl<>(dtoList,pageable,count);
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
                    case "w" : booleanBuilder.or(board.username.contains(keyword));
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
