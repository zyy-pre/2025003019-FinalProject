package com.example.dailyinspiration.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyinspiration.viewmodel.DetailViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    collectionId: Long,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var thoughtInput by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf<Long?>(null) }
    val context = LocalContext.current

    LaunchedEffect(collectionId) {
        viewModel.loadQuote(collectionId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("收藏详情", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    uiState.quote?.let { quote ->
                        IconButton(onClick = {
                            shareQuoteAsPoster(context, quote.text, quote.author, quote.emotionTag)
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "分享海报")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("加载中...", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(uiState.error ?: "未知错误", color = MaterialTheme.colorScheme.error)
                }
            }
            uiState.quote != null -> {
                val quote = uiState.quote!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = "\"${quote.text}\"",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 32.sp,
                                letterSpacing = 0.3.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "— ${quote.author}",
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = quote.emotionTag,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )

                            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "收藏于 ${dateFormat.format(Date(quote.collectedTime))}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            shareQuoteAsPoster(context, quote.text, quote.author, quote.emotionTag)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("生成海报并分享", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "今日镜像",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "这句话让我想到了...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = thoughtInput,
                                onValueChange = { thoughtInput = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("写下你的感想...") },
                                minLines = 3,
                                maxLines = 6,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    viewModel.addJournal(quote.id, thoughtInput)
                                    thoughtInput = ""
                                },
                                enabled = thoughtInput.isNotBlank(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("保存随笔")
                            }
                        }
                    }

                    if (uiState.journals.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "我的随笔",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        uiState.journals.forEach { journal ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = journal.userThought,
                                        modifier = Modifier.weight(1f),
                                        style = MaterialTheme.typography.bodyMedium,
                                        lineHeight = 20.sp
                                    )
                                    IconButton(
                                        onClick = {
                                            showDeleteDialog = journal.id
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "删除",
                                            modifier = Modifier.size(16.dp),
                                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
                                        )
                                    }
                                }
                                val dateFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
                                Text(
                                    text = dateFormat.format(Date(journal.createdAt)),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    showDeleteDialog?.let { journalId ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("删除随笔", fontWeight = FontWeight.Bold) },
            text = { Text("确定要删除这条随笔吗？") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteJournal(journalId)
                    showDeleteDialog = null
                }) {
                    Text("删除", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("取消")
                }
            }
        )
    }
}

private fun shareQuoteAsPoster(context: Context, quote: String, author: String, emotionTag: String) {
    val width = 1080
    val height = 1080
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val bgPaint = Paint().apply {
        color = android.graphics.Color.parseColor("#1A1A2E")
        style = Paint.Style.FILL
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    val quotePaint = Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 56f
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
    }

    val authorPaint = Paint().apply {
        color = android.graphics.Color.parseColor("#AAAAAA")
        textSize = 40f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        isAntiAlias = true
    }

    val emotionPaint = Paint().apply {
        color = android.graphics.Color.parseColor("#006E1F")
        textSize = 36f
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
    }

    val datePaint = Paint().apply {
        color = android.graphics.Color.parseColor("#666666")
        textSize = 32f
        isAntiAlias = true
    }

    val dateStr = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()).format(Date())

    val maxWidth = width - 200f
    val quoteLines = wrapText("\"$quote\"", quotePaint, maxWidth)

    val totalTextHeight = quoteLines.size * 70f + 80f + 60f + 60f
    var startY = (height - totalTextHeight) / 2f + 40f

    quoteLines.forEach { line ->
        canvas.drawText(line, 100f, startY, quotePaint)
        startY += 70f
    }

    startY += 20f
    canvas.drawText("— $author", 100f, startY, authorPaint)
    startY += 80f
    canvas.drawText(emotionTag, 100f, startY, emotionPaint)
    startY += 60f
    canvas.drawText(dateStr, 100f, startY, datePaint)

    val watermarkPaint = Paint().apply {
        color = android.graphics.Color.parseColor("#333333")
        textSize = 28f
        isAntiAlias = true
    }
    canvas.drawText("今日灵感 · DailyInspiration", 100f, height - 60f, watermarkPaint)

    try {
        val file = File(context.cacheDir, "quote_poster.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "分享海报"))
    } catch (_: Exception) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "\"$quote\" — $author\n\n$emotionTag\n\n来自「今日灵感」")
            }
            context.startActivity(Intent.createChooser(shareIntent, "分享名言"))
        } catch (_: Exception) {
        }
    }
}

private fun wrapText(text: String, paint: Paint, maxWidth: Float): List<String> {
    val lines = mutableListOf<String>()
    val words = text.split(" ")
    var currentLine = ""

    for (word in words) {
        val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
        if (paint.measureText(testLine) <= maxWidth) {
            currentLine = testLine
        } else {
            if (currentLine.isNotEmpty()) {
                lines.add(currentLine)
            }
            currentLine = word
        }
    }
    if (currentLine.isNotEmpty()) {
        lines.add(currentLine)
    }
    return lines.ifEmpty { listOf(text) }
}
