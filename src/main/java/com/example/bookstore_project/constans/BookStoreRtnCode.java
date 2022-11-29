package com.example.bookstore_project.constans;

public enum BookStoreRtnCode {
	
	SUCCESSFUL("200","���\"),
	CREATE_SUCCESSFUL("200"," �Ыئ��\"),
	UPDATE_SUCCESSFUL("200"," ��s���\"),
	Delete_SUCCESSFUL("200"," �R������"),
	ISBN_REQUIRED("400"," ISBN���ର��,�εL��ISBN:"),
	ISBN_EXISTED("403"," ��ISBN�w�Q�ϥ�: "),
	ISBNPOWER_EXISTED("403"," �L���v��"),
	NAME_REQUIRED("400"," �ѦW���ର��,�εL���ѦW"),
	WRITER_REQUIRED("400","�@�̤��ର��,�εL���@��"),
	ISBN_NAME_WRITER_REQUIRED("400"," �S���o��ISBN,�ѦW,�@��"),
	PRICE_REQUIRED("400"," �������ର��,�Τp��0"),
	CATEGORY_REQUIRED("400","���O���ର��"),
	STORAGE_REQUIRED("400"," �w�s���ର��,�Τp��0"),
	NUM_REQUIRED("400"," �ʶR�ƶq����p��0,�ά���"),
	STORAGE_ERRO("400"," �w�s�p���ʶR�ƶq,�Э��s�ʶR"),
	NO_FOUND_ISBN("400"," �L��ISBN"),
	NO_CATEGORY("400"," �L�����O:"),
	BUYBOOS_ERRO("400"," �п�J�ʶR�����y�P�ƶq");
	
	
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
