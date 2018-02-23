package com.example.panage.dynamo.filter

import com.example.panage.dynamo.util.DateTimeHolder
import org.springframework.stereotype.Component
import javax.servlet.*

/**
 * @author fu-taku
 */
@Component
class OperationDateTimeFilter : Filter {

    override fun init(filterConfig: FilterConfig?) {
        // No operation
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, filterChain: FilterChain) {
        DateTimeHolder.at {
            filterChain.doFilter(request, response)
        }
    }

    override fun destroy() {
        // No operation
    }

}
