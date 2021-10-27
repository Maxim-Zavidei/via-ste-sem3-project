package Tier3;

import java.io.Serializable;

public class Request implements Serializable
{
  private static final long serialVersionUID = 1L;
  private  Object obj;
  private String requestType;

  public Request(String requestType,Object obj)
  {
    this.requestType = requestType;
    this.obj = obj;
  }

  public Object getObject()
  {
    return obj;
  }
  public String getRequest()
  {
    return requestType;
  }
}