package net.i2cat.seg.extension.model;

import javax.xml.bind.annotation.XmlRootElement;

import net.i2cat.seg.echo.model.Message;

@XmlRootElement(namespace = "net.i2cat.seg.echo.model.extension")
public class ExtendedMessage extends Message {
	
	private long date;

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (date ^ (date >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtendedMessage other = (ExtendedMessage) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (date != other.date)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExtendedMessage [subject="
				+ subject + ", body=" + body + ", date=" + date + "]";
	}
}
