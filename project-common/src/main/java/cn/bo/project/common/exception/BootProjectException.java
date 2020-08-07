package cn.bo.project.common.exception;

public class BootProjectException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BootProjectException(String message){
		super(message);
	}

	public BootProjectException(Throwable cause)
	{
		super(cause);
	}

	public BootProjectException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
