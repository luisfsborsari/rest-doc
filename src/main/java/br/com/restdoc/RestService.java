package br.com.restdoc;

import java.util.List;

/**
 * @author Danilo Buiatti Lamounier
 * 
 *         06/06/2011 11:08:41
 */
public class RestService {

	private String pathURI;
	private String description;
	private String serviceMethod;
	private String requestType;
	private String returns;
	private String returnExample;
	private List<String> parameters;
	private List<String> exceptionThrows;
	private List<String> calls;

	private RestService(String pathURI, String description, String serviceMethod, String requestType,
			String returnTag, String returnExample, List<String> parameters, List<String> exceptionThrows,
			List<String> calls) {
		this.pathURI = pathURI;
		this.description = description;
		this.serviceMethod = serviceMethod;
		this.requestType = requestType;
		this.returns = returnTag;
		this.returnExample = returnExample;
		this.parameters = parameters;
		this.exceptionThrows = exceptionThrows;
		this.calls = calls;
	}

	public static class Builder {
		private String pathURI;
		private String description;
		private String serviceMethod;
		private String requestType;
		private String returns;
		private String returnExample;
		private List<String> parameters;
		private List<String> exceptionThrows;
		private List<String> callsTag;

		public Builder(String pathURI, String requestType) {
			this.pathURI = pathURI;
			this.requestType = requestType;
		}

		public Builder() {
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withServiceMethod(String serviceMethod) {
			this.serviceMethod = serviceMethod;
			return this;
		}

		public Builder wihtParameters(List<String> parameters) {
			this.parameters = parameters;
			return this;
		}

		public Builder withReturns(String returns) {
			this.returns = returns;
			return this;
		}

		public Builder withReturnExample(String returnExample) {
			this.returnExample = returnExample;
			return this;
		}

		public Builder withExceptionThrows(List<String> exceptionThrows) {
			this.exceptionThrows = exceptionThrows;
			return this;
		}

		public Builder withCalls(List<String> calls) {
			this.callsTag = calls;
			return this;
		}

		public RestService build() {
			return new RestService(pathURI, description, serviceMethod, requestType, returns, returnExample,
					parameters, exceptionThrows, callsTag);
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calls == null) ? 0 : calls.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((serviceMethod == null) ? 0 : serviceMethod.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((pathURI == null) ? 0 : pathURI.hashCode());
		result = prime * result + ((returnExample == null) ? 0 : returnExample.hashCode());
		result = prime * result + ((returns == null) ? 0 : returns.hashCode());
		result = prime * result + ((exceptionThrows == null) ? 0 : exceptionThrows.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestService other = (RestService) obj;
		if (calls == null) {
			if (other.calls != null)
				return false;
		} else if (!calls.equals(other.calls))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (requestType == null) {
			if (other.requestType != null)
				return false;
		} else if (!requestType.equals(other.requestType))
			return false;
		if (serviceMethod == null) {
			if (other.serviceMethod != null)
				return false;
		} else if (!serviceMethod.equals(other.serviceMethod))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (pathURI == null) {
			if (other.pathURI != null)
				return false;
		} else if (!pathURI.equals(other.pathURI))
			return false;
		if (returnExample == null) {
			if (other.returnExample != null)
				return false;
		} else if (!returnExample.equals(other.returnExample))
			return false;
		if (returns == null) {
			if (other.returns != null)
				return false;
		} else if (!returns.equals(other.returns))
			return false;
		if (exceptionThrows == null) {
			if (other.exceptionThrows != null)
				return false;
		} else if (!exceptionThrows.equals(other.exceptionThrows))
			return false;
		return true;
	}

}
