apollo client 3

Most of the types and values are moved upward to the topmost module
to improve ergonomics.

Note that react apollo client hooks return `js.UndefOr[T]` while the
standard query functions return `js.UndefOr[T|Null]`.

I'm also trying out some new facade scala file naming schemes
to see if they help with cracking down types and values more
easily.