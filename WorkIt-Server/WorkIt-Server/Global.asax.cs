using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Routing;
using WorkIt_Server.Handlers;

namespace WorkIt_Server
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            GlobalConfiguration.Configuration.MessageHandlers.Add(new
                ApplicationAuthenticationHandler());
            GlobalConfiguration.Configure(WebApiConfig.Register);
        }
    }
}
