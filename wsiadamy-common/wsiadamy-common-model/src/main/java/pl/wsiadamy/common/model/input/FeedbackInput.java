package pl.wsiadamy.common.model.input;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class FeedbackInput {
	
	@NotNull
	@Range(min=1, max=5, message="{javax.validation.constraints.required}")
	private Integer value;
	
	@Length(min=0, max=200, message="{javax.validation.constraints.required}")
	private String comment;
	
    public FeedbackInput() {
    	setValue(0);
    	setComment(null);
    }

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
    
}
