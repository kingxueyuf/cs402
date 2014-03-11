/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;

import java.util.Set;

public interface WebRequest {
    public void setMethod(String method);
    public void setPath(String path);
    public void setVersion(String version);
    public String getMethod();
    public String getPath();
    public String getVersion();
    public void addHeader(String name, String value);
    public String getHeader(String name);
    public Set<String> getHeaderNames();
    public UniformResourceLocator getUrl();
    public void setUrl(UniformResourceLocator url);
}