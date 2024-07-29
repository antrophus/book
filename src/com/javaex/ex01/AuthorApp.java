package com.javaex.ex01;

import java.util.List;

public class AuthorApp {

	public static void main(String[] args) {

		//메모리에 Dao 올리기
		AuthorDao authorDao = new AuthorDao();
		
		//insert (등록)
		authorDao.insertAuthor("황일영", "학원강사");
		
		//delete (삭제)
		authorDao.deleteAuthor(9);
		
		//selectAll (전체 출력)
		List<AuthorVo> authorList = authorDao.selectAuthorAll();
		System.out.println(authorList);
		//이름만 출력
		for(int i = 0; i < authorList.size(); i++){
			System.out.println(authorList.get(i).getAuthorName());
		}
		
		//updateAuthor (수정)
		authorDao.updateAuthor(8, "유재석", "무한도전");
		
		//select (하나만 선택)
		authorDao.selectAuthor(5);
		
		
		
		
		
		
		
	}

}
