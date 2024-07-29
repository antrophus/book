package com.javaex.ex02;

import java.util.List;

public class BookApp {

	public static void main(String[] args) {
		
		BookDao bookDao = new BookDao();
		
		//*등록 수정 *삭제 조회1 *조회전체
		// 등록
//		bookDao.insertBook("뇌","열린책들","2023.6.20", 10 );
		
		// 수정
		bookDao.updateBook(10, "꿀벌의 예언", "열린 책들", "2023-06-20", 10);
		
		// 삭제
//		bookDao.deleteBook(9);
		
		// 전체조회
		List<BookVo> bookList = bookDao.selectBookAll();
		System.out.println(bookList);
		for(int i = 0; i<bookList.size(); i++) {
			System.out.println(bookList.get(i).getTitle());
		}
		
		// 조회 1개
		BookVo bookVo = bookDao.selectBook(6);
		System.out.println(bookVo);

	}

}
