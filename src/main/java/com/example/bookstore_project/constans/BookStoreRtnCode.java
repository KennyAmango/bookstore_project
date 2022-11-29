package com.example.bookstore_project.constans;

public enum BookStoreRtnCode {
	
	SUCCESSFUL("200","成功"),
	CREATE_SUCCESSFUL("200"," 創建成功"),
	UPDATE_SUCCESSFUL("200"," 更新成功"),
	Delete_SUCCESSFUL("200"," 刪除完成"),
	ISBN_REQUIRED("400"," ISBN不能為空,或無此ISBN:"),
	ISBN_EXISTED("403"," 此ISBN已被使用: "),
	ISBNPOWER_EXISTED("403"," 無此權限"),
	NAME_REQUIRED("400"," 書名不能為空,或無此書名"),
	WRITER_REQUIRED("400","作者不能為空,或無此作者"),
	ISBN_NAME_WRITER_REQUIRED("400"," 沒有這個ISBN,書名,作者"),
	PRICE_REQUIRED("400"," 價錢不能為空,或小於0"),
	CATEGORY_REQUIRED("400","類別不能為空"),
	STORAGE_REQUIRED("400"," 庫存不能為空,或小於0"),
	NUM_REQUIRED("400"," 購買數量不能小於0,或為空"),
	STORAGE_ERRO("400"," 庫存小於購買數量,請重新購買"),
	NO_FOUND_ISBN("400"," 無此ISBN"),
	NO_CATEGORY("400"," 無此類別:"),
	BUYBOOS_ERRO("400"," 請輸入購買的書籍與數量");
	
	
    private String code;
	
	private String message;
	
	private BookStoreRtnCode(String code,String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	

}
