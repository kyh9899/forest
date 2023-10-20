package com.hm.forest.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hm.forest.board.model.service.BoardService;
import com.hm.forest.board.model.vo.Reply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	private BoardService boardService;

	// 비동기 통신 응답
	// 게시글에 달린 댓글 목록 조회
	@GetMapping("/{boardNo}")
	public ResponseEntity<Map<String, Object>> getRepliesByBoardNo(@PathVariable("boardNo") int boardNo) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("listCount", boardService.selectReplyCountByBoardNo(boardNo));
		map.put("replies", boardService.getRepliesByBoardNo(boardNo));
			
		return ResponseEntity.ok(map);
	}
			
	// 특정 댓글 조회
	@GetMapping("/search/{replyNo}")
	@ResponseBody
	public Map<String, Object> getReplyByNo(@PathVariable("replyNo") int replyNo) {
		Map<String, Object> map = new HashMap<>();

		map.put("reply", boardService.getReplyByNo(replyNo));
			
		return map;
	}
	
	
	// 댓글 등록
	@PostMapping("/enroll")
	public ResponseEntity<Map<String, Object>> enroll(@RequestBody Reply reply)  {
		int result = 0;
		
		System.out.println(reply);
		System.out.println(reply.getCreateDate());
		
		Map<String, Object> map = new HashMap<>();
		
		result = boardService.save(reply);
		
		map.put("resultCode", result);
		map.put("reply", reply);
	
		
		return ResponseEntity.ok(map);
	}
	
	// 댓글 수정
		@PostMapping("/update")
		public ResponseEntity<Map<String, Object>> update(@RequestParam("replyNo") int replyNo, @RequestParam("content") String content)  {
			Map<String, Object> map = new HashMap<>();
			int result = 0;
			Reply reply = null;
			System.out.println(replyNo);
			System.out.println(content);
			
			reply = boardService.getReplyByNo(replyNo);
			
			reply.setContent(content);
			
			result = boardService.save(reply);
			
			 if (result > 0) {
		            map.put("message", "성공");
		     } else {
		            map.put("message", "실패");
		     }
			
			
			 
			map.put("resultCode", result);
			map.put("reply", reply);
		
			return ResponseEntity.ok(map);
		}
		
		
		// 댓글 수정 테스트
		@GetMapping("/updateTest")
		public ModelAndView updateTest (ModelAndView modelAndView) {
			
			modelAndView.addObject("pageName", "boardUpdate");
			modelAndView.setViewName("page/board/update");
			
			return modelAndView;
		}
		
		@GetMapping("/delete/{replyNo}")
		@ResponseBody
		public Map<String, Object> delete(@PathVariable("replyNo") int replyNo) {
		    Map<String, Object> map = new HashMap<>();
		    int result = 0;
		    Reply reply = boardService.getReplyByNo(replyNo);
		    
		    if (reply != null) {
		        // 해당 댓글이 존재하면 삭제
		        result = boardService.deleteReply(reply.getNo());

		        if (result > 0) {
		            map.put("message", "성공");
		        } else {
		            map.put("message", "실패");
		        }
		    } else {
		        map.put("message", "댓글을 찾을 수 없습니다.");
		    }
		    
		    map.put("location", "/board/view?no=" + reply.getBoardNo());
		    
		    return map;
		}

}
