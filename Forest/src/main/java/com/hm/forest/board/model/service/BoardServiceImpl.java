package com.hm.forest.board.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.forest.board.model.mapper.BoardMapper;
import com.hm.forest.board.model.vo.Board;
import com.hm.forest.board.model.vo.Reply;
import com.hm.forest.common.util.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	// 게시판 타입별 게시글 전체 개수
	@Override
	public int selectBoardCountByType(String type) {
		return boardMapper.selectBoardCountByType(type);
	}

	// 게시판 타입별 게시글 전체 목록 조회
	@Override
	public List<Board> getBoardLists(String type, PageInfo pageInfo) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		return boardMapper.selectBoardListsByType(type, bounds);
	}
	
	// [검색값 o] 게시판 타입별 게시글 전체 개수
	@Override
	public int selectBoardCountBySearchValue(String type, String searchType, String keyWord) {
		return boardMapper.selectBoardCountBySearchValue(type, searchType, keyWord);
	}
	
	// [검색값 o] 게시판 타입별 게시글 전체 목록 조회
	@Override
	public List<Board> getBoardListsBySearchValue(String type, PageInfo pageInfo, String searchType, String keyWord) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		return boardMapper.selectBoardListsBySearchValue(type, bounds, searchType, keyWord);
	}

	// 특정 게시글 조회
	@Override
	public Board getBoardByNo(int no) {
		return boardMapper.selectBoardByNo(no);
	}

	// 게시글 등록, 게시글 수정
	@Override
	@Transactional
	public int save(Board board) {
		int result = 0;
		
		if (board.getNo() > 0) {
			// update
			result = boardMapper.updateBoard(board);
		} else {
			// insert
			result = boardMapper.insertBoard(board);
		}	
		return result;
	}

	// 게시글 삭제
	@Override
	@Transactional
	public int delete(int no) {
		return boardMapper.updateStatus(no, "N");
	}

	// 게시글 조회수 업데이트
	@Override
	@Transactional
	public int updateReadCount(int no) {
		return boardMapper.updateReadCount(no);
	}

	// 댓글 등록, 댓글 수정
	@Override
	@Transactional
	public int save(Reply reply) {

			int result = 0;
			
			if (reply.getNo() > 0) {
				// update		
				result = boardMapper.updateReply(reply);
			} else {
				// insert
				result = boardMapper.insertReply(reply);
			}
			
			return result;
	}

	// 댓글 리스트 조회
	@Override
	public List<Reply> getRepliesByBoardNo(int boardNo) {
		return boardMapper.selectRepliesByBoardNo(boardNo);
	}

	// 게시글별 댓글 수 조회
	@Override
	public int selectReplyCountByBoardNo(int boardNo) {
		return boardMapper.selectReplyCountByBoardNo(boardNo);
	}

	// 특정 댓글 조회
	@Override
	public Reply getReplyByNo(int no) {
		return boardMapper.selectReplyByNo(no);
	}

	// 댓글 삭제
	@Override
	@Transactional
	public int deleteReply(int no) {
		return boardMapper.updateReplyStatus(no, "N");
	}

	// 게시글 전체 갯수
	@Override
	public int selectboardcount() {

		return boardMapper.selectboardcount();
	}

	// 게시글 전체 조회
	@Override
	public List<Board> getboardlist(PageInfo pageInfo) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return boardMapper.getboardlist(rowBounds);
	}

	// 검색 게시글 갯수
	@Override
	public int selectboardcountsearch(String searchType, String keyWord) {

		return boardMapper.selectboardcountsearch(searchType, keyWord);
	}

	// 검색 게시글 조회
	@Override
	public List<Board> getboardlistsearch( PageInfo pageInfo, String searchType, String keyWord) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1)* limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return boardMapper.getboardlistsearch(searchType, keyWord, rowBounds);
	}

	// 게시글 삭제
	@Override
	public int updatboardstatus(String status, int no) {

		return boardMapper.updatboardstatus("N", no);
	}

	// 게시글 계시
	@Override
	public int activateboard(String status, int no) {

		return boardMapper.activateboard("Y", no);
	}

	// 댓글 전체 갯수
	@Override
	public int selectreplycount() {

		return boardMapper.selectreplycount();
	}

	// 댓글 전체 조회
	@Override
	public List<Board> getreplylists(PageInfo pageInfo) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);

		return boardMapper.getreplylists(rowBounds);
	}

	// 검색 댓글 갯수
	@Override
	public int selectreplycountsearch(String searchType) {

		return boardMapper.selectreplycountsearch(searchType);
	}

	// 검색 댓글 조회
	@Override
	public List<Board> getreplylistsearch(String searchType, PageInfo pageInfo) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);

		return boardMapper.getreplylistsearch(searchType, rowBounds);
	}





}
