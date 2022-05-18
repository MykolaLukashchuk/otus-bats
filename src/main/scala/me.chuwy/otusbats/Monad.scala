package me.chuwy.otusbats

import scala.concurrent.{ExecutionContext, Future}

trait Monad[F[_]] extends Functor[F] { self =>
  def map[A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(a => point(f(a)))

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def point[A](a: A): F[A]

  def flatten[A](fa: F[F[A]]): F[A] = flatMap(fa)(f => f)
}

object Monad {
  implicit def listMonad(implicit ev: Monad[List]): Monad[List] = new Monad[List] {
    override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)

    override def point[A](a: A): List[A] = List(a)
  }

  implicit def optionMonad(implicit ev: Monad[Option]): Monad[Option] = new Monad[Option] {
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)

    override def point[A](a: A): Option[A] = Option(a)
  }

  implicit def optionMonad(implicit ev: Monad[Future], executionContext: ExecutionContext): Monad[Future] = new Monad[Future] {
    override def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa.flatMap(f)

    override def point[A](a: A): Future[A] = Future(a)
  }
}