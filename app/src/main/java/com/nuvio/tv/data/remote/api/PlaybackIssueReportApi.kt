package us.xnetik.tv.data.remote.api

import us.xnetik.tv.data.remote.dto.PlaybackIssueReportRequestDto
import us.xnetik.tv.data.remote.dto.PlaybackIssueReportResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PlaybackIssueReportApi {
    @POST("api/playback-reports")
    suspend fun createPlaybackIssueReport(
        @Body body: PlaybackIssueReportRequestDto
    ): Response<PlaybackIssueReportResponseDto>
}
