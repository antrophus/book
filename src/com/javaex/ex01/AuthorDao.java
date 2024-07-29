package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao { // 기능 위주의 클래스

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

	// 메소드 일반
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

	// 작가 등록
	public int insertAuthor(String name, String desc) {
		System.out.println("등록 로직");

		int count = -1;

		// 0. import java.sql.*;
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비(insert 문을 자바의 문자열로 만든다.)
			// String query = "insert into author value(null, '이말년','웹툰작가')";
			String query = "";
			query += " insert into author ";
			query += " value(null, ?, ?) ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, desc);

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
	}// 작가 등록

	// 작가 삭제
	public int deleteAuthor(int authorId) {
		System.out.println("삭제 로직");

		int count = -1;

		// 0. import java.sql.*;
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " delete from author ";
			query += " where author_id = ? ";

			// *바인딩

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + " 건 처리 완료");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 자원 정리 메소드 호출
		this.close();

		return count;
	}// 작가 삭제

	// 작가 업데이트
	public int updateAuthor(int authorId, String name, String desc) {
		System.out.println("수정 로직");

		int count = -1;

		// 0. import java.sql.*;
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " update author ";
			query += " set author_name = ? , ";
			query += " author_desc	= ? ";
			query += " where author_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, desc);
			pstmt.setInt(3, authorId);

			// *실행
			count = pstmt.executeUpdate();
//			rs = pstmt.executeQuery();

			// 4.결과처리
			System.out.println(count + " 건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원 정리 메소드 호출
		this.close();

		return count;
	}// 작가 업데이트

	// 작가 정보 하나 가져오기
	public int selectAuthor(int authorId) {
		System.out.println("선택한 작가 정보");

		int count = -1;
		// 0. import java.sql.*;
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select author_id ";
			query += " 		 ,author_name ";
			query += "		 ,author_desc ";
			query += " from author ";
			query += " where author_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);

			// *실행 - executeUpdate 하고 헷갈릴 수 있다.
			rs = pstmt.executeQuery();

			// 4.결과처리
			rs.next(); // 커서를 옮겨서 필드명 밑의 데이터를 가리키게 해야함
			int aid = rs.getInt("author_id");
			String name = rs.getString("author_name");
			String desc = rs.getString("author_desc");
			AuthorVo authorVo = new AuthorVo(aid, name, desc);
			System.out.println(authorVo);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 자원 정리 메소드 호출
		this.close();

		return count;

	}// 작가 정보 하나 가져오기 끝.

	// 작가 정보 다 가져오기
	public List<AuthorVo> selectAuthorAll() {
		System.out.println("작가전체리스트");
		// 리스트만들기
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		// 0. import java.sql.*;
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select author_id ";
			query += " 		 ,author_name ";
			query += "		 ,author_desc ";
			query += " from author ";
			query += " order by author_id asc ";

			// *바인딩
			pstmt = conn.prepareStatement(query);

			// *실행 - executeUpdate 하고 헷갈릴 수 있다.
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기 - while 반복문 이용해서 전부 출력
			// authroVo 작가수만큼 만들기
			while (rs.next()) {
				int aid = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");

				// 리스트 리턴
				AuthorVo authorVo = new AuthorVo(aid, name, desc);
				// 위에 만든 것을 add
				authorList.add(authorVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 자원 정리 메소드 호출
		this.close();

		// 리스트의 주소를 리턴해준다.
		return authorList;

	}

}
