.class public Lpicoapp453/picoctf/com/picoapp/ToasterActivity;
.super Landroid/support/v7/app/ActionBarActivity;
.source "ToasterActivity.java"


# instance fields
.field mystery:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 2

    .prologue
    .line 11
    invoke-direct {p0}, Landroid/support/v7/app/ActionBarActivity;-><init>()V

    .line 14
    new-instance v0, Ljava/lang/String;

    const/16 v1, 0x21

    new-array v1, v1, [C

    fill-array-data v1, :array_0

    invoke-direct {v0, v1}, Ljava/lang/String;-><init>([C)V

    iput-object v0, p0, Lpicoapp453/picoctf/com/picoapp/ToasterActivity;->mystery:Ljava/lang/String;

    return-void

    :array_0
    .array-data 0x2
        0x66t 0x0t
        0x6ct 0x0t
        0x61t 0x0t
        0x67t 0x0t
        0x20t 0x0t
        0x69t 0x0t
        0x73t 0x0t
        0x3at 0x0t
        0x20t 0x0t
        0x77t 0x0t
        0x68t 0x0t
        0x61t 0x0t
        0x74t 0x0t
        0x5ft 0x0t
        0x64t 0x0t
        0x6ft 0x0t
        0x65t 0x0t
        0x73t 0x0t
        0x5ft 0x0t
        0x74t 0x0t
        0x68t 0x0t
        0x65t 0x0t
        0x5ft 0x0t
        0x6ct 0x0t
        0x6ft 0x0t
        0x67t 0x0t
        0x63t 0x0t
        0x61t 0x0t
        0x74t 0x0t
        0x5ft 0x0t
        0x73t 0x0t
        0x61t 0x0t
        0x79t 0x0t
    .end array-data
.end method


# virtual methods
.method public displayMessage(Landroid/view/View;)V
    .locals 3
    .parameter "view"

    .prologue
    .line 43
    invoke-virtual {p0}, Lpicoapp453/picoctf/com/picoapp/ToasterActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "Toasters don\'t toast toast, toast toast toast!"

    const/4 v2, 0x1

    invoke-static {v0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 44
    const-string v0, "Debug tag"

    iget-object v1, p0, Lpicoapp453/picoctf/com/picoapp/ToasterActivity;->mystery:Ljava/lang/String;

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 45
    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 18
    invoke-super {p0, p1}, Landroid/support/v7/app/ActionBarActivity;->onCreate(Landroid/os/Bundle;)V

    .line 19
    const v0, 0x7f030018

    invoke-virtual {p0, v0}, Lpicoapp453/picoctf/com/picoapp/ToasterActivity;->setContentView(I)V

    .line 20
    return-void
.end method

.method public onCreateOptionsMenu(Landroid/view/Menu;)Z
    .locals 2
    .parameter "menu"

    .prologue
    .line 26
    invoke-virtual {p0}, Lpicoapp453/picoctf/com/picoapp/ToasterActivity;->getMenuInflater()Landroid/view/MenuInflater;

    move-result-object v0

    const/high16 v1, 0x7f0c

    invoke-virtual {v0, v1, p1}, Landroid/view/MenuInflater;->inflate(ILandroid/view/Menu;)V

    .line 27
    const/4 v0, 0x1

    return v0
.end method

.method public onOptionsItemSelected(Landroid/view/MenuItem;)Z
    .locals 2
    .parameter "item"

    .prologue
    .line 35
    invoke-interface {p1}, Landroid/view/MenuItem;->getItemId()I

    move-result v0

    .line 36
    .local v0, id:I
    const v1, 0x7f07003d

    if-ne v0, v1, :cond_0

    .line 37
    const/4 v1, 0x1

    .line 39
    :goto_0
    return v1

    :cond_0
    invoke-super {p0, p1}, Landroid/support/v7/app/ActionBarActivity;->onOptionsItemSelected(Landroid/view/MenuItem;)Z

    move-result v1

    goto :goto_0
.end method
