package org.hoteia.qalingo.core.web.mvc.form;

import org.springframework.web.multipart.MultipartFile;

public class CmsContentBlockForm {
	
	protected String id;
	protected String cmcContentId;

    protected String type;
    protected int ordering;
    protected boolean active;
    
    protected String title;
    protected String text;
    protected String params;
    
    protected MultipartFile file;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCmcContentId() {
		return cmcContentId;
	}
	
	public void setCmcContentId(String cmcContentId) {
		this.cmcContentId = cmcContentId;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getOrdering() {
		return ordering;
	}
	
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	
	public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getParams() {
        return params;
    }
	
	public void setParams(String params) {
        this.params = params;
    }
	
	public MultipartFile getFile() {
		return file;
	}
	
	public void setFile(MultipartFile file) {
		this.file = file;
	}

}