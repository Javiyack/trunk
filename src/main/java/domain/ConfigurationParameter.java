package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationParameter extends DomainEntity{

	private String termAndConditions;

	public String getTermAndConditions() {
		return termAndConditions;
	}

	public void setTermAndConditions(String termAndConditions) {
		this.termAndConditions = termAndConditions;
	}
	
}
