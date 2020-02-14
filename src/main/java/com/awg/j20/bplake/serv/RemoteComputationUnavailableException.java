package com.awg.j20.bplake.serv;

public class RemoteComputationUnavailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String remoteCallError;

	public RemoteComputationUnavailableException(String remoteError) {
		super();
		this.remoteCallError = remoteError;	
	}

	public String getRemoteCallError() {
		return remoteCallError;
	}
}
