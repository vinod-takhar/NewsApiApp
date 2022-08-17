package com.demoapps.newsapp.ui.screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demoapps.newsapp.R
import com.demoapps.newsapp.data.state.AppState
import com.demoapps.newsapp.model.Article
import com.demoapps.newsapp.ui.state.ErrorItem
import com.demoapps.newsapp.ui.state.LoadingItem
import com.demoapps.newsapp.ui.state.LoadingView
import com.demoapps.newsapp.ui.theme.color1
import com.demoapps.newsapp.ui.theme.color2
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleListPagingScreen(mainViewModel: MainViewModel, onArticleDetails: (Article) -> Unit){
	val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
	val coroutineScope = rememberCoroutineScope()

	var uiState = mainViewModel.uiOptionsForFilterState

	ModalBottomSheetLayout(
		sheetState = bottomSheetState,
		sheetContent = {
			BottomSheet(mainViewModel,uiState,bottomSheetState,coroutineScope)
		}){
		Scaffold(
			topBar = {
				TopAppBar(
					title = {
						Text(text = "News API App")
					},
					actions = {
						IconButton(onClick = {
							coroutineScope.launch {
								bottomSheetState.show()
							}
						}) {
							Image(
								painter = painterResource(R.drawable.ic_filter),
								contentDescription = "Filter News"
							)
						}
					},
					navigationIcon = {
						Card(
							modifier = Modifier.size(48.dp),
							shape = CircleShape,
							elevation = 2.dp
						) {
							Image(
								painter = painterResource(R.drawable.ic_launcher_round),
								contentDescription = "Launcher Icon"
							)
						}
					},
					backgroundColor = color2,
				)
			}, content = {
				Column(modifier = Modifier.padding(4.dp)){
					ArticleList(articles = mainViewModel.articles,onArticleDetails)
				}
			})

	}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
	mainViewModel: MainViewModel,
	uiState: AppState,
	bottomSheetState: ModalBottomSheetState,
	coroutineScope: CoroutineScope,
//		onNewUpdate: (AppState) -> Unit
) {
	var mQueryKeyword by remember { mutableStateOf(uiState.searchQuery) }
	var mSearchIn by remember { mutableStateOf(uiState.searchIn) }
	var mSortBy by remember { mutableStateOf(uiState.sortBy) }
	var mSearchFrom by remember { mutableStateOf(uiState.searchFrom) }

	Box(
		Modifier
			.fillMaxWidth(),
		contentAlignment = Alignment.Center
	) {
		Column(Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
			Text("Filter News", fontSize = 20.sp,color = color1, fontWeight = FontWeight.Bold,modifier = Modifier.padding(8.dp), )
			Spacer(modifier = Modifier
				.fillMaxWidth()
				.height(2.dp)
				.background(color2))
			Card(modifier = Modifier.padding(8.dp),shape = RectangleShape) {
				SearchBar(mQueryKeyword,onQueryChange = { mQueryKeyword = it })
			}

			Row(
				Modifier
					.fillMaxWidth()
					.wrapContentHeight()) {
				Column(
					Modifier
						.fillMaxWidth()
						.weight(1f),horizontalAlignment = Alignment.CenterHorizontally){
					Text("Search In", fontSize = 16.sp,color = color1, fontStyle = FontStyle.Italic,modifier = Modifier.padding(4.dp))
					SearchInQueryDropDown(mSearchIn,onSearchInChange = { mSearchIn = it })
				}
				Column(
					Modifier
						.fillMaxWidth()
						.weight(1f),horizontalAlignment = Alignment.CenterHorizontally){
					Text("Sort By", fontSize = 16.sp,color = color1, fontStyle = FontStyle.Italic,modifier = Modifier.padding(4.dp))
					SortByDropDown(mSortBy, onSortByChange = { mSortBy = it })
				}
			}

			Row(
				Modifier
					.fillMaxWidth()
					.wrapContentHeight(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
				Text("Search From", fontSize = 16.sp,color = color1, fontStyle = FontStyle.Italic,modifier = Modifier.padding(4.dp))
				FromDatePicker(mSearchFrom,onSearchFromChange = { mSearchFrom = it })
			}

			Spacer(modifier = Modifier
				.fillMaxWidth()
				.height(2.dp)
				.background(color2))

			Button(modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 30.dp, vertical = 10.dp),
				onClick = {
					coroutineScope.launch { bottomSheetState.hide()
//						onNewUpdate(AppState(mQueryKeyword,mSearchIn,mSortBy,mSearchFrom))
						mainViewModel.updateAppState(newAppState = AppState(mQueryKeyword,mSearchIn,mSortBy,mSearchFrom))
					}
				}
			) {
				Text("Apply")
			}
		}

	}

}

@Composable
fun FromDatePicker(mSearchFrom: String,onSearchFromChange: (String) -> Unit) {
	// Fetching the Local Context
	val mContext = LocalContext.current

	// for year, month and day
	val mYear: Int
	val mMonth: Int
	val mDay: Int

	// Fetching current year, month and day
	val mCalendar = Calendar.getInstance()
	mYear = mCalendar.get(Calendar.YEAR)
	mMonth = mCalendar.get(Calendar.MONTH)
	mDay = mCalendar.get(Calendar.DAY_OF_MONTH) - 10 // for default 10 days back

	mCalendar.time = Date()

	// Declaring DatePickerDialog and setting
	// initial values as current values (present year, month and day)
	val mDatePickerDialog = DatePickerDialog(
		mContext,
		{ _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
			onSearchFromChange("$mYear-${mMonth+1}-$mDayOfMonth")
		}, mYear, mMonth, mDay
	)

	Card(modifier = Modifier.padding(8.dp),shape = RectangleShape){Row(verticalAlignment = Alignment.CenterVertically) {
		Text(mSearchFrom,
			Modifier
				.clickable {
					mDatePickerDialog.show()
				}
				.padding(8.dp))
		Icon(
			Icons.Filled.ArrowForward,"contentDescription",
			Modifier.clickable { mDatePickerDialog.show() })
	}}
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(query: String,onQueryChange: (String) -> Unit) {

	val keyboardController = LocalSoftwareKeyboardController.current

	TextField(
		singleLine = true,
		value = query,
		keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
		keyboardActions = KeyboardActions(
			onDone = {keyboardController?.hide()}),
		onValueChange = onQueryChange,
		leadingIcon = {
			Icon(imageVector = Icons.Default.Search,contentDescription = null)
		},
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = MaterialTheme.colors.surface
		),
		placeholder = {
			Text(stringResource(R.string.placeholder_search))
		},
		label = {
			Text(stringResource(R.string.search_query))
		},
		modifier = Modifier
			.fillMaxWidth()
			.heightIn(min = 56.dp)
	)
}

@Composable
fun SearchInQueryDropDown(mSearchIn: String,onSearchInChange: (String) -> Unit) {
	var mExpanded by remember { mutableStateOf(false) }

	val mSearchInOptions = listOf("title", "description", "content")

	// Up Icon when expanded and down icon when collapsed
	val icon = if (mExpanded)
		Icons.Filled.KeyboardArrowUp
	else
		Icons.Filled.KeyboardArrowDown

	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Card(modifier = Modifier.padding(8.dp),shape = RectangleShape){Row(verticalAlignment = Alignment.CenterVertically) {
			Text(mSearchIn,
				Modifier
					.clickable {
						mExpanded = !mExpanded
					}
					.padding(8.dp))
			Icon(icon,"contentDescription",
				Modifier.clickable { mExpanded = !mExpanded })
		}}
		DropdownMenu(
			expanded = mExpanded,
			onDismissRequest = { mExpanded = false },
		) {
			mSearchInOptions.forEach { label ->
				DropdownMenuItem(onClick = {
//						mSelectedText = label
					onSearchInChange(label)
					mExpanded = false
				}) {
					Text(text = label)
				}
			}
		}
	}
}

@Composable
fun SortByDropDown(mSortBy: String,onSortByChange: (String) -> Unit) {
	var mExpanded by remember { mutableStateOf(false) }

	val mSearchInOptions = listOf("relevancy", "popularity", "publishedAt")

	// Up Icon when expanded and down icon when collapsed
	val icon = if (mExpanded)
		Icons.Filled.KeyboardArrowUp
	else
		Icons.Filled.KeyboardArrowDown

	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Card(modifier = Modifier.padding(8.dp),shape = RectangleShape){Row(verticalAlignment = Alignment.CenterVertically) {
			Text(mSortBy,
				Modifier
					.clickable {
						mExpanded = !mExpanded
					}
					.padding(8.dp))
			Icon(icon,"contentDescription",
				Modifier.clickable { mExpanded = !mExpanded })
		}}
		DropdownMenu(
			expanded = mExpanded,
			onDismissRequest = { mExpanded = false },
		) {
			mSearchInOptions.forEach { label ->
				DropdownMenuItem(onClick = {
//						mSelectedText = label
					onSortByChange(label)
					mExpanded = false
				}) {
					Text(text = label)
				}
			}
		}
	}
}

@Composable
fun ArticleList(articles: Flow<PagingData<Article>>, onArticleDetails: (Article) -> Unit) {
	val lazyArticleItems = articles.collectAsLazyPagingItems()

	val isRefreshing by mutableStateOf(false)

	SwipeRefresh(
		state = rememberSwipeRefreshState(isRefreshing),
		onRefresh = {lazyArticleItems.refresh() },
		indicator = { state, trigger ->
			SwipeRefreshIndicator(
				// Pass the SwipeRefreshState + trigger through
				state = state,
				refreshTriggerDistance = trigger,
				// Enable the scale animation
				scale = true,
				// Change the color and shape
				backgroundColor = MaterialTheme.colors.primary,
				shape = MaterialTheme.shapes.small,
			)
		}
	) {
		LazyColumn(
		) {

			items(lazyArticleItems) { article ->
				ArticleView(article = article!!,onArticleDetails)
				Divider(color = Color.Gray)
			}

			lazyArticleItems.apply {
				when {
					loadState.refresh is LoadState.Loading -> {
						item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
					}
					loadState.append is LoadState.Loading -> {
						item { LoadingItem() }
					}
					loadState.refresh is LoadState.Error -> {
						val e = lazyArticleItems.loadState.refresh as LoadState.Error
						item {
							ErrorItem(
								message = e.error.message!!,
								modifier = Modifier.fillParentMaxSize(),
								onClickRetry = { retry() }
							)
						}
					}
					loadState.append is LoadState.Error -> {
						val e = lazyArticleItems.loadState.append as LoadState.Error
						item {
							ErrorItem(
								message = e.error.message!!,
								onClickRetry = { retry() }
							)
						}
					}
				}
			}
		}
	}
}

@Composable
fun ArticleView(article: Article, onArticleDetails: (Article) -> Unit) {
	Column() {
		Row(
			modifier = Modifier
				.padding(start = 16.dp, bottom = 16.dp, top = 16.dp, end = 16.dp)
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			if (article.urlToImage != null && article.urlToImage.isNotEmpty())
				ArticleImage(
					article.urlToImage,
					modifier = Modifier
						.padding(end = 16.dp)
						.size(90.dp)
				)
			Column(modifier = Modifier.weight(1f)){
				ArticleTitle(
					article.title!!,
				)
				Text(
					text = "Read more...",
					maxLines = 3,
					textAlign = TextAlign.End,
					style = MaterialTheme.typography.h6,
					color= Color.Blue,
					overflow = TextOverflow.Ellipsis,
					modifier = Modifier.fillMaxWidth().padding(all = 4.dp).clickable {
						onArticleDetails(article)
					}
				)
			}
		}
		Row(
			Modifier
				.fillMaxWidth()
				.wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween) {
			if(article.author != null)Text(
				text = "Author: ${article.author}",
				maxLines = 1,
				textAlign = TextAlign.End,
				style = MaterialTheme.typography.subtitle1,
				overflow = TextOverflow.Ellipsis,
			)
			if(article.source != null)Text(
				text = "Source: ${article.source.name}",
				maxLines = 1,
				textAlign = TextAlign.Start,
				style = MaterialTheme.typography.subtitle1,
				overflow = TextOverflow.Ellipsis,
			)
		}
		Text(
			text = "Published at: ${article.publishedAt}",
			maxLines = 1,
			textAlign = TextAlign.End,
			style = MaterialTheme.typography.caption,
			modifier = Modifier
				.fillMaxWidth()
				.padding(all = 4.dp)
		)
	}
}

@Composable
fun ArticleImage(
	imageUrl: String,
	modifier: Modifier = Modifier
) {
	AsyncImage(
		model = ImageRequest.Builder(LocalContext.current)
			.data(imageUrl)
			.crossfade(true)
			.build(),
		placeholder = painterResource(com.demoapps.newsapp.R.drawable.ic_photo),
		contentDescription = null,
		contentScale = ContentScale.Crop,
		modifier = modifier
	)
	
}

@Composable
fun ArticleTitle(
	title: String,
	modifier: Modifier = Modifier
) {
	Text(
		modifier = modifier,
		text = title,
		maxLines = 3,
		style = MaterialTheme.typography.h6,
		overflow = TextOverflow.Ellipsis
	)
}