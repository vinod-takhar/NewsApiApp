import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demoapps.newsapp.model.Article
import com.demoapps.newsapp.ui.screen.ArticleListPagingScreen
import com.demoapps.newsapp.ui.screen.MainViewModel
import com.google.gson.Gson

@Composable
fun NewsAppNavHost(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "article_list"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("article_list") {
            ArticleListPagingScreen(mainViewModel,onArticleDetails = { navigateToArticleDetails(navController,it) })
        }
        composable(
            route = "article_detail/{article}",
            arguments = listOf(
                navArgument("article") {
                    type = Article.NavigationType
                }
            )
        ) { backStackEntry ->
            val article = backStackEntry.arguments?.getParcelable<Article>("article")
            ArticleDetailScreen(navController,article!!)
        }
    }
}

fun navigateToArticleDetails(navController: NavHostController, article: Article){
    val articleJson = Uri.encode(Gson().toJson(article))
    try {
        Log.e("EEEXXX", "is null ${article == null}")
        Log.e("EEEXXX", "is publishedAt null ${article.publishedAt == null}")
        Log.e("EEEXXX", "is author null ${article.author == null}")
        Log.e("EEEXXX", "is title null ${article.title == null}")
        Log.e("EEEXXX", "is content null ${article.content == null}")
        Log.e("EEEXXX", "is description null ${article.description == null}")
        Log.e("EEEXXX", "is url null ${article.url == null}")
        Log.e("EEEXXX", "is urlToImage null ${article.urlToImage == null}")
        Log.e("EEEXXX", "is source null ${article.source == null}")
        Log.e("EEEXXX", "is null ${article.source!!.name == null}")
        Log.e("EEEXXX", "is null ${article.source!!.id == null}")
    }catch (e:Error){
        e.printStackTrace();
    }

    Log.e("EEEXXX","is null ${article.hashCode()}")

    navController.navigate("article_detail/$articleJson")
}