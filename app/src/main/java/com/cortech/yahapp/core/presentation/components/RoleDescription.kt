package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.R
import com.cortech.yahapp.core.data.model.jobs.JobPosition

@Composable
fun RoleDescription(
    jobPosition: JobPosition = JobPosition()
) {
    Box(
        Modifier
            .padding(12.dp)
            .background(Color(0xFF2D2D2D), RoundedCornerShape(10))
            .padding(horizontal = 12.dp, vertical = 8.dp)) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = jobPosition.jobTitle,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(jobPosition.description, color = Color.White)
            Spacer(modifier = Modifier.padding(8.dp))
            Text("${stringResource(id = R.string.client)}: ${jobPosition.client}", color = Color.White)
            Spacer(modifier = Modifier.padding(8.dp))
            Text("${stringResource(id = R.string.interview_type)} ${jobPosition.typeOfInterview}", color = Color.White)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = stringResource(id = R.string.skills),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            SplitLazyColumns(jobPosition.skillsRequired)
        }
    }
}

@Preview
@Composable
fun RoleDescriptionPreview() {
    RoleDescription()
}