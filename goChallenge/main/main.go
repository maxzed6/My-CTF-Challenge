package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"io/ioutil"
	"net"
	"os"
	"text/template"
	"time"
)

type User struct {
	name   string
	secret string
}

var flag = os.Getenv("FLAG")

func isAdmin(c *gin.Context) {
	c.JSON(200, gin.H{
		"msg": "因为你不是，所以你才是。",
	})
}

func index(c *gin.Context) {
	name := c.PostForm("name")
	if name == "" {
		c.JSON(200, gin.H{
			"msg": "what's your name?post your 'name'",
		})
		return
	}
	user := User{name: name, secret: "/1s_s0urc3"}
	tpl, err := template.New("").Parse("李在赣神魔？" + user.name)
	if err != nil {
	}
	tpl.Execute(c.Writer, &user)
	return
}

func proxy(c *gin.Context) {
	name, _ := c.GetQuery("name")
	conn, err := net.Dial("tcp", "127.0.0.1:8888")
	conn.SetReadDeadline(time.Now().Add(3 * time.Microsecond * 1000))
	if err != nil {
		fmt.Println("err :", err)
		return
	}
	packet := "GET /isAdmin?name=" + name + " HTTP/1.1\r\n"
	packet += "Host: 127.0.0.1\r\n\r\n"
	fmt.Println(packet)
	_, err = conn.Write([]byte(packet))
	if err != nil {
		fmt.Println("recv failed, err:", err)
		return
	}
	buf := [512]byte{}
	defer conn.Close()
	var result string
	for {
		n, err := conn.Read(buf[:])
		if err != nil {
			c.JSON(200, gin.H{
				"msg": result,
			})
			return
		}
		result += string(buf[:n])
	}
}

func source(c *gin.Context) {
	data, err := ioutil.ReadFile("main.go")
	if err != nil {
		fmt.Print("File read error")
	}
	c.JSON(200, gin.H{
		"msg": data,
	})
}

func getFlag(c *gin.Context) {
	ip, _, err := net.SplitHostPort(c.Request.RemoteAddr)
	name := c.PostForm("name")
	if err != nil {
		fmt.Println("error occur")
	}
	if ip != "127.0.0.1" {
		c.JSON(200, gin.H{
			"msg": "内部使用",
		})
		return
	}
	fmt.Println(name)
	if name != "admin" {
		c.JSON(200, gin.H{
			"msg": "管理员内部使用",
		})
		return
	}
	c.JSON(200, gin.H{
		"msg": "逮到flag了" + flag,
	})
}

func main() {
	r := gin.Default()
	r.GET("/", index)
	r.POST("/", index)
	r.GET("/1s_s0urc3", source)
	r.GET("/proxy", proxy)
	r.GET("/isAdmin", isAdmin)
	r.POST("/getFlag", getFlag)
	r.Run(":8888")
}
