package com.example.bookstore_project.constans;

public enum BookStoreRtnCode {
	
	SUCCESSFUL("200","成功"),
	CREATE_SUCCESSFUL("200","創建成功"),
	UPDATE_SUCCESSFUL("200","更新成功"),
	Delete_SUCCESSFUL("200","刪除完成"),
	ID_REQUIRED("400","ID不能為空"),
	ID_EXISTED("錯誤403","此ID已被使用: "),
	NAME_REQUIRED("400","名字不能為空"),
	WRITER_REQUIRED("400","作者不能為空"),
	PRICE_REQUIRED("400","價錢不能為空,或小於0"),
	CATEGORY_REQUIRED("400","類別不能為空"),
	STORAGE_REQUIRED("400","庫存不能為空,或小於0"),
	NUM_REQUIRED("400","請輸入購買數量");
	
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
