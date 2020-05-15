package ftl.run.status

import com.google.api.services.testing.model.TestExecution
import com.google.api.services.testing.model.TestMatrix
import ftl.args.IArgs
import ftl.util.MatrixState
import ftl.util.StopWatch
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.SystemOutRule

class TestMatrixStatusPrinterTest {
    @Rule
    @JvmField
    val systemOutRule = SystemOutRule().enableLog()!!

    @Test
    fun test() {
        // given
        val time = "time"
        val testMatricesIds = (0..1).map(Int::toString)
        val matrices = testMatricesIds.mapIndexed { index, s ->
            TestMatrix().apply {
                testMatrixId = s
                state = MatrixState.FINISHED
                testExecutions = (0..index).map { TestExecution() }
            }
        }
        val args = mockk<IArgs> {
            every { outputStyle } returns OutputStyle.Single
        }
        val stopWatch = mockk<StopWatch>(relaxed = true) {
            every { check(any()) } returns time
        }
        val printExecutionStatusList = mockk<(String, List<TestExecution>?) -> Unit>(relaxed = true)
        val printMatrices = TestMatrixStatusPrinter(
            testMatricesIds = testMatricesIds,
            args = args,
            stopWatch = stopWatch,
            printExecutionStatusList = printExecutionStatusList
        )
        val expected = listOf(
            "",
            """
  time 0 FINISHED
  time 1 FINISHED
"""
        )
        // when
        testMatricesIds.forEachIndexed { index, _ ->
            printMatrices(matrices[index])

            // then
            verify { stopWatch.check(true) }
            verify { printExecutionStatusList(time, matrices[index].testExecutions) }
            assertEquals(expected[index], systemOutRule.log.filterMockk())
        }
    }
}
