# easychat api

## User api
| 說明          | Method   | token  | path                         | param     |
| ------------- | -------- |:------:|:---------------------------- | --------- |
| `Register`    | `POST`   |        | `/api/user/register`         |           |
| `Login`       | `POST`   |        | `/api/user/login`            |           |
| `Search User` | `GET`    | `need` | `/api/user/search/{user_id}` | `user_id` |
| `Delete User` | `DELETE` | `need` | `/api/delete/{user_id}`      | `user_id` |

**Request 參數說明**
- `user_id`:`int`
- `Register` & `Login` 皆需要RequestBody，格式如下
    ```json
    {
        "username":"your_username",
        "password":"your_password"
    }
    ```

## Friend api
| 說明              | Method   | token  | path                                     | param                  |
| ----------------- |:-------- |:------:|:---------------------------------------- | ---------------------- |
| `Add friend`      | `POST`   | `need` | `/api/friend//add/{userid}/{friendid}`   | `user_id`, `friend_id` |
| `Get friend List` | `GET`    | `need` | `/api/friend/getlist/{userid}`           | `user_id`              |
| `Delete friend`   | `DELETE` | `need` | `/api/friend/delete/{userid}/{friendid}` | `user_id`, `friend_id` |

**Request 參數說明**
- `user_id`:`int`

## Chat api(WIP)



     
