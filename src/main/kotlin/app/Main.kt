package app

import api.movies.MovieEndpoints
import api.notices.NoticeEndpoints
import api.users.UserEndpoints

fun main(args: Array<String>) {


    MovieEndpoints.initialize()
    UserEndpoints.initialize()
    NoticeEndpoints.initialize()




}
