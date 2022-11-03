package com.example.bookstore_project.constans;

public enum BookStoreRtnCode {
	
	SUCCESSFUL("200","���\"),
	CREATE_SUCCESSFUL("200","�Ыئ��\"),
	UPDATE_SUCCESSFUL("200","��s���\"),
	Delete_SUCCESSFUL("200","�R������"),
	ID_REQUIRED("400","ID���ର��"),
	ID_EXISTED("���~403","��ID�w�Q�ϥ�: "),
	NAME_REQUIRED("400","�W�r���ର��"),
	WRITER_REQUIRED("400","�@�̤��ର��"),
	PRICE_REQUIRED("400","�������ର��,�Τp��0"),
	CATEGORY_REQUIRED("400","���O���ର��"),
	STORAGE_REQUIRED("400","�w�s���ର��,�Τp��0"),
	NUM_REQUIRED("400","�п�J�ʶR�ƶq");
	
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
