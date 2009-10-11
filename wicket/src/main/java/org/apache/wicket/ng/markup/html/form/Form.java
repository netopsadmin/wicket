package org.apache.wicket.ng.markup.html.form;

import org.apache.wicket.ng.Component;
import org.apache.wicket.ng.request.Request;
import org.apache.wicket.ng.request.RequestHandler;
import org.apache.wicket.ng.request.cycle.RequestCycle;
import org.apache.wicket.ng.request.handler.PageAndComponentProvider;
import org.apache.wicket.ng.request.handler.impl.BookmarkableListenerInterfaceRequestHandler;
import org.apache.wicket.ng.request.handler.impl.ListenerInterfaceRequestHandler;
import org.apache.wicket.ng.request.response.Response;

public class Form extends Component implements IFormSubmitListener
{
	private static final long serialVersionUID = 1L;

	public Form(String id)
	{
		super(id);
	}

	public void onFormSubmitted()
	{
		Request r = RequestCycle.get().getRequest();
		System.out.println(r.getRequestParameters().getParameterValue("key1"));
	}
	
	@Override
	public void renderComponent()
	{
		Response response = RequestCycle.get().getResponse();
		
		response.write("<form action=\"" + getURL() + "\" method=\"post\">\n");
		response.write("<input type=\"hidden\" name=\"key1\" value=\"value1\">\n");
		response.write("<input type=\"hidden\" name=\"key2\" value=\"value2\">\n");
		response.write("<input type=\"submit\" value=\"Submit\">");
		response.write("</form>");
	}

	private boolean bookmarkable;

	public void setBookmarkable(boolean bookmarkable)
	{
		this.bookmarkable = bookmarkable;
	}

	public boolean isBookmarkable()
	{
		return bookmarkable;
	}

	private String getURL()
	{
		RequestHandler handler;
		PageAndComponentProvider provider = new PageAndComponentProvider(getPage(), this);
		if (isBookmarkable())
		{
			handler = new BookmarkableListenerInterfaceRequestHandler(provider, IFormSubmitListener.INTERFACE);
		}
		else
		{
			handler = new ListenerInterfaceRequestHandler(provider, IFormSubmitListener.INTERFACE);
		}
		return RequestCycle.get().renderUrlFor(handler);
	}
}