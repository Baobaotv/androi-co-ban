package com.KMA.BookingCare.Api;

import com.KMA.BookingCare.Dto.CommentDto;
import com.KMA.BookingCare.Entity.CommentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Service.CommentService;

import java.util.List;

@Controller
public class CommentApi {

	private final Logger log = LoggerFactory.getLogger(CommentApi.class);
	
	@Autowired
	private CommentService commentService;

	
	@PostMapping(value = {"/comment/delete"})
	public ResponseEntity<?> deleteHandbook(@RequestBody Long id) {
		try {
			commentService.delete(id);
			System.out.println("đã xoá cmt");
			
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println("không thể xoá cmt");
		}
		return ResponseEntity.ok("true");
	}

	@GetMapping(value = "/api/comment/handbook/{id}")
	public ResponseEntity<?> getAllById(@PathVariable("id") Long id) {
		log.debug("Request to get comment by handbook id {}", id);
		if (commentService.existsByHandbookId(id)) {
			List<CommentDto> lst = commentService.findAllByHandbookId(id);
			return  ResponseEntity.ok(lst);
		} else {
			return ResponseEntity.noContent().build();
		}

	}
}
