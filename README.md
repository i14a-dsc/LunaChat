# LunaChat

チャンネルチャットプラグイン<br />

> [!WARNING]
> LunaChatとMultiverse 5.xを同時に使用しない場合はこのフォークを使用する意味はありません。

> [!IMPORTANT]
> 本プラグインをビルドするには、事前にGitHub Packagesから依存関係をダウンロードするための認証設定が必要です。
> 事前に `~/.m2/settings.xml` を作成または編集し、以下のようにご自身のGitHubアカウント情報を設定してください。
> PATの発行はこちらから: https://github.com/settings/tokens

~/.m2/settings.xmlの例

```xml
<settings>
    <servers>
        <server>
            <id>github</id>
            <username>YOUR_GITHUB_USERNAME</username>
            <password>YOUR_PERSONAL_ACCESS_TOKEN</password>
        </server>
    </servers>
</settings>
```

# How to compile

事前に以下のツールを用意してください:

- Apache Maven 3.9.x
- Git
- Java 17.x

```bash
git clone https://github.com/i14a-dsc/LunaChat
cd LunaChat
mvn package
```

成果物は `./target/LunaChat.jar` に生成されます。

# その他

コマンドリファレンスや、設定リファレンスは、こちらから<br />
https://github.com/ucchyocean/LunaChat/wiki/Commands<br />
https://github.com/ucchyocean/LunaChat/wiki/Config<br />
<br />
本プラグインのライセンスは、LGPLv3に従います。ライセンス条文は下記を参照。<br />
http://sourceforge.jp/magazine/07/09/05/017211<br />
