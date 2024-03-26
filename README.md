# JavaCalCommand
このリポジトリはLinuxの"cal"コマンドを模倣したJavaアプリケーションが含まれています。

## 実行方法
プログラムを実行するにはシステムにJavaがインストールされていることを確認してください。
以下のコマンドでアプリケーションを実行します。

>java -Dfile.encoding=UTF-8 -cp . JavaCalCommand.java

特定の年のカレンダーを表示するには、cal <年>（例：cal 2024）と入力してください。

また特定の年、月を指定し表示する際は、cal <月> <年>（例：cal 3 2024）と入力してください。

もちろん、cal のみの入力は現在の月のカレンダーが表示されます。


プログラムを終了するには**exit**と入力します。

## 工夫した点
- calコマンドの引数(年、月等)によって指定されたカレンダーを出力
- 不適切な入力に関し、Linux環境同様の適切なエラーメッセージを提供 
- 入力値のチェック、カレンダー生成、カレンダー表示と役割ごとのメソッド分け
  
