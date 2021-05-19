package com.giaynhap.securechat.exception;

public class ApiOptException  extends ApiException{

   public enum OptExceptionCode {
        INCORRECT(0),
        EXPIRED(1),
        SEND(2),
        UNKNOWN(3);
        protected final int value;
        private OptExceptionCode(int value) {
            this.value = value;
        }

        public String message(){
            switch (this){
                case SEND:
                    return "Send new OTP";
                case EXPIRED:
                    return "OTP Expired";
                case INCORRECT:
                    return "OTP Incorrect";
            }
            return "OTP Unkonwn Error";
        }

        public static OptExceptionCode valueOf(int code){
            switch (code){

                case 0:
                    return INCORRECT;
                case 1:
                    return EXPIRED;
                case 2:
                    return SEND;
            }
            return UNKNOWN;
        }
    }
    private Long data;
    public ApiOptException() {
        super(100 + OptExceptionCode.UNKNOWN.value, OptExceptionCode.UNKNOWN.message());
    }
    public OptExceptionCode getError(){
        return OptExceptionCode.valueOf(this.getCode() - 100);
    }
    public ApiOptException(OptExceptionCode code) {
        super(code.value + 100,code.message());
    }
    public ApiOptException(OptExceptionCode code, Long data) {
        super(code.value + 100,code.message());
        this.data = data;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }
}
