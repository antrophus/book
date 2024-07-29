package com.javaex.ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class BookDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/book_db";
	private String id = "book";
	private String pw = "1234";

	// 생성자

	// 메소드 gs

	// 메소드 일반

	// DB연결 메소드
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원 정리 메소드
	private void close() {
		// 5. 자원정리 메소드
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 책 등록
	public int insertBook(String title, String pubs, String pubDate, int authorId) {
		System.out.println("책 등록");

		int count = -1;
		// 0. DB 연결
		this.getConnection();

		try {
			// 1. Driver 연결
			// 2. Db 접속 url: 아이디: 비번:
			// 3. SQL문 준비 / 바인딩 /실행
			String query = "";
			query += " insert into book ";
			query += " value(null, ?, ?, ? ,?) ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, pubs);
			pstmt.setString(3, pubDate);
			pstmt.setInt(4, authorId);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + " 건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원 정리 메소드 호출
		this.close();

		return count;
	}// 책 등록(끝)

	// 책 수정
	public int updateBook(int bookId, String title, String pubs, String pubDate, int authorId) {
		System.out.println("책 수정");
		int count = -1;
		// 0. DB 연결
		this.getConnection();
		try {
			// 1. Driver 연결
			// 2. Db 접속 url: 아이디: 비번:
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " update book ";
			query += " set title = ? ";
			query += " 	  ,pubs = ? ";
			query += " 	  ,pub_date = ? ";
			query += "    ,author_Id = ? ";
			query += " where book_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, pubs);
			pstmt.setString(3, pubDate);
			pstmt.setInt(4, authorId);
			pstmt.setInt(5, bookId);

			// *실행
			count = pstmt.executeUpdate();
//			rs = pstmt.executeQuery();

			// 4.결과처리
			System.out.println(count + " 건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원 정리 메소드 호출
		this.close();

		return count;
	}// 책 수정(끝)

	// 책 삭제
	public int deleteBook(int bookId) {
		System.out.println("책 삭제");

		int count = -1;
		// 0. DB 연결
		this.getConnection();

		try {
			// 1. Driver 연결
			// 2. Db 접속 url: 아이디: 비번:
			// 3. SQL문 준비 / 바인딩 /실행
			// *sql문 준비
			String query = "";
			query += " delete from book ";
			query += " where book_id = ? ";

			// *바인딩

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + " 건 삭제 완료");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 자원 정리 메소드 호출
		this.close();

		return count;
	}// 책 삭제(끝)

	// 책 정보 다 가져오기
	public List<BookVo> selectBookAll() {
		System.out.println("책 전체 리스트");
		//리스트 만들기
		List<BookVo> bookList = new ArrayList<BookVo>();
		
		//db컨넥트
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select 	 b.book_id ";
			query += " 		 	,b.title ";
			query += "		 	,b.pubs ";
			query += " 			,b.pub_date ";
			query += " 			,a.author_name ";
			query += " 			,a.author_desc ";
			query += " 			,a.author_id ";
			query += " from book b, author a ";
			query += " where a.author_id = b.author_id ";

				
			// *바인딩
			pstmt = conn.prepareStatement(query);

			// *실행 - executeUpdate 하고 헷갈릴 수 있다.
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			// 리스트로 만들기 - while 반복문 이용해서 전부 출력
			// BookVo 책 수 만큼 만들기
			while (rs.next()) {
				int bId = rs.getInt("b.book_id");
				String title = rs.getString("b.title");
				String pubs = rs.getString("b.pubs");
				String pubDate = rs.getString("b.pub_date");
				String name = rs.getString("a.author_name");
				String desc = rs.getString("a.author_desc");
				int authorId = rs.getInt("a.author_id");

				// 리스트 리턴
				BookVo bookVo = new BookVo(bId, title, pubs, pubDate, authorId, name, desc );
				// 위에 만든 것을 add
				bookList.add(bookVo);
			}
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		return bookList;
		
	}// 책 정보 다 가져오기(끝)
	
	// 책 정보 하나 가져오기
	
		public BookVo selectBook(int bookId) {
			System.out.println("고른 책 정보 불러오기");
			BookVo bookVo = null;
			
			// 0. import java.sql.*;
			this.getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				// *sql문 준비
				String query = "";
				query += " select 	 b.book_id ";
				query += " 			,b.title ";
				query += " 			,b.pubs ";
				query += " 			,b.pub_date ";
				query += " 			,a.author_name ";
				query += " 			,a.author_desc ";
				query += " 			,a.author_id ";
				query += " from book b, author a ";
				query += " where a.author_id = b.author_id ";
				query += " and book_id = ? ";
				
				// *바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, bookId);
				
				// *실행
				rs = pstmt.executeQuery();
				
				// 4.결과처리
				while (rs.next()) {
					int bId = rs.getInt("b.book_id");
					String title = rs.getString("b.title");
					String pubs = rs.getString("b.pubs");
					String pubDate = rs.getString("b.pub_date");
					String aName = rs.getString("a.author_name");
					String aDesc = rs.getString("a.author_desc");
					int aId = rs.getInt("a.author_id");
					
					bookVo = new BookVo(bId, title, pubs, pubDate, aId, aName, aDesc);
				}
				
				
			} catch(SQLException e) {
				System.out.println("error:" + e);
			}
			
			//자원 정리 메소드 호출
			this.close();
			
			return bookVo;
			
		}

}
