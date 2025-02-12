package egovframework.com.cmm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * SimpleCORSFilter.java 클래스
 *
 * @author 신용호
 * @since 2019. 10. 18.
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일                수정자               수정내용
 *  ----------   ----------   ----------------------
 *  2019.10.18   신용호                최초 생성
 * </pre>
 */
@WebFilter(urlPatterns = "*.do")
public class SimpleCORSFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);
	//private final List<String> allowedOrigins = Arrays.asList("http://localhost:9700");

	public SimpleCORSFilter() {
		log.info("SimpleCORSFilter init");
	}
	
	@Value("${cors.allowed-origin}") 
	private String allowedOrigin;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		log.debug("===>>> SimpleCORSFilter > doFilter()");
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;

		// Access-Control-Allow-Origin
		String origin = request.getHeader("Origin");

		log.debug("===>>> origin = " + allowedOrigin);

		if (origin != null && !origin.equals("")) {
			origin = origin.replace("\r", "").replace("\n", "");// Security - Potential HTTP Response Splitting 분할응답 조치
		}

		if (origin != null && origin.equals(allowedOrigin)) {
            response.setHeader("Access-Control-Allow-Origin", allowedOrigin);
        }
		
		//response.setHeader("Set-Cookie", "JSESSIONID=" + response.encodeURL("JSESSIONID") + "; Path=/; HttpOnly; Secure; SameSite=None");

		// Access-Control-Max-Age
		response.setHeader("Access-Control-Max-Age", "3600");

		// Access-Control-Allow-Credentials
		response.setHeader("Access-Control-Allow-Credentials", "true");

		// Access-Control-Allow-Methods
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

		// Access-Control-Allow-Headers
		response.setHeader("Access-Control-Allow-Headers",
			"Origin, X-Requested-With, Content-Type, Accept, " + "X-CSRF-TOKEN");

		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {}

	@Override
	public void destroy() {}

}