using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Claims;
using System.Threading;
using System.Web;
using WorkIt_Server.Models.Context;

namespace WorkIt_Server.Handlers
{
    public class ApplicationAuthenticationHandler : DelegatingHandler
    {
        private const string InvalidToken = "Invalid Authorization-Token";
        private const string MissingToken = "Missing Authorization-Token";

        protected override System.Threading.Tasks.Task<HttpResponseMessage> SendAsync(HttpRequestMessage
 request, System.Threading.CancellationToken cancellationToken)
        {
            if (request.RequestUri.ToString().Contains("api/auth"))
            {
                return base.SendAsync(request, cancellationToken);
            }

            IEnumerable<string> sampleApiKeyHeaderValues = null;

            if (request.Headers.TryGetValues("authToken", out sampleApiKeyHeaderValues))
            {
                string[] apiKeyHeaderValue = sampleApiKeyHeaderValues.First().Split(':');

                if (apiKeyHeaderValue.Length == 2)
                {
                    if (!int.TryParse(apiKeyHeaderValue[0], out int userId))
                    {
                        return requestCancel(request, cancellationToken, InvalidToken);
                    }

                    var accessToken = apiKeyHeaderValue[1];

                    string realAccessToken;

                    using (var db = new WorkItDbContext())
                    {
                        realAccessToken = db.Users.FirstOrDefault(u => u.UserId == userId).AccessToken;
                    }

                    if (realAccessToken == accessToken)
                    {
                        return base.SendAsync(request, cancellationToken);
                    }
                    else
                    {
                        return requestCancel(request, cancellationToken, InvalidToken);
                    }
                }
                else
                {
                    return requestCancel(request, cancellationToken, MissingToken);

                }
            }
            else
            {
                return requestCancel(request, cancellationToken, MissingToken);
            }
        }

        private System.Threading.Tasks.Task<HttpResponseMessage> requestCancel(HttpRequestMessage
request, System.Threading.CancellationToken cancellationToken, string message)
        {
            CancellationTokenSource _tokenSource = new CancellationTokenSource();
            cancellationToken = _tokenSource.Token;
            _tokenSource.Cancel();
            HttpResponseMessage response = new HttpResponseMessage();
            response = request.CreateResponse(HttpStatusCode.BadRequest);
            response.Content = new StringContent(message);

            return base.SendAsync(request, cancellationToken).ContinueWith(task =>
            {
                return response;
            });
        }
    }
}