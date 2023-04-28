# easychat api

## 簡易前端示意圖
![image](https://user-images.githubusercontent.com/52891597/235135855-5d8d0a77-952e-4522-989c-7dee2d62d1d1.png)



## User api
| 說明          | Method   | token  | path                         |
| ------------- | -------- |:------ |:---------------------------- |
| `Register`    | `POST`   |        | `/api/user/register`         |
| `Login`       | `POST`   |        | `/api/user/login`            |
| `Search User` | `GET`    |        | `/api/user/search/{user_name}` |
| `Delete User` | `DELETE` | `need` | `/api/delete/{user_id}`      |

**參數說明**
- `user_id`:`int`
- `user_name`:`String`
- `Register` & `Login` 皆需要RequestBody，格式如下
    ```json
    {
        "username":"your_username",
        "password":"your_password"
    }
    ```
- `login`之後會得到一組`jwt token` 需要`token`的`api`請帶入`Header`的`Authorization`欄位並加上前綴`Bearer`。

## Friend api
| 說明              | Method   | token  | path                                     |
| ----------------- |:-------- |:------ |:---------------------------------------- |
| `Add friend`      | `POST`   | `need` | `/api/friend/add/{userid}/{friendid}`   |
| `Get friend List` | `GET`    | `need` | `/api/friend/get_list/{userid}`          |
| `Delete friend`   | `DELETE` | `need` | `/api/friend/delete/{userid}/{friendid}` |

**參數說明**
- `user_id`:`int`
- `friend_id`:`int`

## Chat api
| 說明           | Method | token  | path                                             |
| -------------- | --- |:------ |:------------------------------------------------ |
| `Get all room` | `GET` |        | `/api/chat/room/get_list`                        |
| `Add new room` | `POST` | `need` | `/api/chat/room/add/{user_id}/{chat_room_name}`  |
| `Delete room`  | `DELETE`  | `need` | `/api/chat/room/delete/{user_id}/{chat_room_id}` |

**參數說明**
- `user_id`:`int`
- `chat_room_name`:`String`
- `chat_room_id`:`int`

## WebSocket


| 說明              | path                           |
| ----------------- |:------------------------------ |
| Connect webSocket | `/websocket/{room}/{username}` |

**參數說明**
- `username`:`String`
- `room`:`String`

     
