package com.baylej.android.data.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baylej.android.data.database.dao.UserDao
import com.baylej.android.data.database.dao.UserDetailDao
import com.baylej.android.data.database.entity.LocationEntity
import com.baylej.android.data.database.entity.UserDetailEntity
import com.baylej.android.data.database.entity.UserEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class DatabaseIT :  KoinTest {

    private val userDatabase: UserDatabase by inject()
    private val userDao: UserDao by inject()
    private val userDetailDao: UserDetailDao by inject()

    @Before
    fun setUp(){
        loadKoinModules(databaseTestModules)
    }

    @After
    fun tearDown(){
        userDatabase.close()
        unloadKoinModules(databaseTestModules)
    }

    @Test
    fun testUserDao() {
        userDao.insertUsers(
            listOf(
                UserEntity(
                    "id1",
                    "Mr",
                    "Rick",
                    "Sanchez",
                    "https://picture.com/rick",
                ),
                UserEntity(
                    "id2",
                    "Mr",
                    "Morty",
                    "Smith",
                    "https://picture.com/morty"
                )
            )
        )
        val users = userDao.getAll()
        Assert.assertNotNull(users)
        Assert.assertEquals(2, users.count())
        Assert.assertEquals(true, users[0].id != users[1].id)
        for (user: UserEntity in users ) {
            if ("id1" == user.id){
                Assert.assertEquals("Rick", user.firstName)
                Assert.assertEquals("Sanchez", user.lastName)
            } else {
                Assert.assertEquals("Morty", user.firstName)
                Assert.assertEquals("Smith", user.lastName)
            }
        }
    }

    @Test
    fun testUserDetailDao() {
        userDetailDao.insertUserDetail(
            UserDetailEntity(
                "id1",
                "male",
                "rick.sanchez@rickandmorty.com",
                "1946-01-26T19:26:49.610Z",
                "2021-06-21T21:02:07.374Z",
                "92694011",
                LocationEntity(
                    "Main street",
                    "Seattle",
                    "Washington",
                    "USA",
                    "-08:00"
                )
            )
        )
        val user1 = userDetailDao.findByUserId("id1")
        Assert.assertNotNull(user1)
        Assert.assertEquals("male", user1?.gender)
        Assert.assertEquals("92694011", user1?.phone)

        val user2 = userDetailDao.findByUserId("unknownId")
        Assert.assertNull(user2)
    }

}