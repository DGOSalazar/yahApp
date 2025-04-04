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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.core.domain.model.auth.CandidateModel

@Composable
fun CandidateProfile(
    modifier: Modifier = Modifier,
    candidateModel: CandidateModel = CandidateModel()
) {
    Box(
        Modifier
            .padding(12.dp)
            .background(Color(0xFF2D2D2D), RoundedCornerShape(10))
            .padding(horizontal = 12.dp, vertical = 8.dp)) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "${candidateModel.name}, ${candidateModel.position}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(candidateModel.summary, color = Color.White)
            Spacer(modifier = Modifier.padding(8.dp))
            Text("Location: ${candidateModel.location}", color = Color.White)
            Spacer(modifier = Modifier.padding(8.dp))
            Text("Salary: ${candidateModel.salary}", color = Color.White)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Skills",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            //SplitLazyColumns(candidateModel.skills)
        }
    }
}

@Preview
@Composable
fun CandidateProfilePreview() {
    CandidateProfile()
}
